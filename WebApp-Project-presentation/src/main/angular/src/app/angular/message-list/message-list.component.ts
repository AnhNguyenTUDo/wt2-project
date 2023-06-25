import { Component, OnInit, Input } from '@angular/core';
import { MessageService } from '../message.service';
import { Message } from '../../message';
import { BasicAuthService } from '../../auth/basic-auth.service'
import { map, catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'app-message-list',
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.sass']
})
export class MessageListComponent  implements OnInit{

    public isEditing: boolean = false;
    public editedMessage: Message;
    public authorizationStatus: { [messageId: string]: boolean} = {};

    @Input()
    public messages: Message[] = [];

    constructor(private messageService: MessageService,
                private authService: BasicAuthService) {
      this.editedMessage = {} as Message;
      }

    ngOnInit() {
      this.getAll();
    }

    getAll() {
        this.messageService.getAll().subscribe((messages) => {
          this.messages = messages;
        });
      }

    toEditMessage(message:Message) {
      this.editedMessage = { ...message};
      this.isEditing = true;
    }

    editMessage() {
      this.messageService.update(this.editedMessage.id, this.editedMessage.content)
          .subscribe(updatedMessage => {
            // Handle the updated message, e.g., display a success message
            console.log('Message updated:', updatedMessage);
            this.isEditing = false;
            this.editedMessage = {} as Message;

            const index = this.messages.findIndex(m => m.id === updatedMessage.id);
                    if (index > -1) {
                      this.messages[index] = updatedMessage;
                    }

          }, error => {
            // Handle the error case, e.g., display an error message
            console.error('Error updating message:', error);
            this.editedMessage = {} as Message;
          });
    }

    deleteMessage(message: Message) {
      this.messageService.delete(message.id)
        .subscribe(() => {
           this.messages = this.messages.filter(m => m.id !== message.id);
           console.log('Message deleted:', message);
        }, error => {
           console.error('Error deleting message:', error);
          });
    }

    canModifyMessage(message: Message): Observable<boolean>{
      const messageId = message.id.toString();
        console.log("in message list component FALSE " + this.authService.isAdmin);
      if (this.authService.isAdmin) {
          console.log("in message list component should be TRUE" + this.authService.isAdmin);
          return of(true);
        }

      if (this.authorizationStatus.hasOwnProperty(messageId)) {
            console.log("status HAS PROPERTY"+this.authorizationStatus[messageId]);
            return of(this.authorizationStatus[messageId]);
          }

      return this.authService.checkAuthorization(messageId).pipe(
          map((response: boolean) => {
          console.log("RESPONSE" + response);
            this.authorizationStatus[messageId] = response;
            return response;
          }),
          catchError((error) => {
            console.error('Error checking authorization:', error);
            return of(false);
          })
        );

//       this.checkAuthorizationStatus(messageId);
//       return false;
     }
//
//       checkAuthorizationStatus(messageId: string) {
//           this.authService.checkAuthorization(messageId).subscribe(
//             (response: boolean) => {
//               this.authorizationStatus[messageId] = response;
//             },
//             (error) => {
//               console.error('Error checking authorization:', error);
//             }
//           );
//         }
//       canModifyMessage(message: Message): Observable<boolean> {
//         // Check if the user is logged in
//         if (!this.authService.isLoggedIn) {
//           return false;
//         }
//
//         // Check if the user has the necessary role or permission to modify the message
//         // Replace this logic with your actual authorization checks
//         this.authService.getUserRole().subscribe((roles: Set<string>) => {
//           // For example, if the user has the 'admin' role, they can modify any message
//           if (roles.has('admin')) {
//             return true;
//           }
//
//           // For non-admin users, check if the message belongs to the authenticated user
//           return this.authService.checkAuthorization(message.id);
//         });
//
//         return false; // Default return value if the authorization check is asynchronous
//       }
}
