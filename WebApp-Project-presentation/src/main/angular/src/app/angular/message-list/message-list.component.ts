import { Component, OnInit, Input, Output } from '@angular/core';
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
  public hasAuthorization: { [messageId: string]: boolean} = {};

  @Input()
  parent: any;

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
        this.isEditing = false;
        this.editedMessage = {} as Message;

        const index = this.messages.findIndex(m => m.id === updatedMessage.id);
          if (index >= 0) {
            this.messages[index] = updatedMessage;
          }
      }, error => {
        console.error('Error updating message:', error);
        this.editedMessage = {} as Message;
      });
  }

  deleteMessage(message: Message) {
    this.messageService.delete(message.id)
      .subscribe(() => {
        this.messages = this.messages.filter(m => m.id !== message.id);
      }, error => {
         console.error('Error deleting message:', error);
        }
      );
  }

  canModifyMessage(message: Message): Observable<boolean>{
    const messageId = message.id.toString();
    if (this.authService.isAdmin) {
        return of(true);
    }

    if (this.hasAuthorization.hasOwnProperty(messageId)) {
          return of(this.hasAuthorization[messageId]);
    }

    return this.authService.checkAuthorization(messageId).pipe(
        map((response: boolean) => {
          this.hasAuthorization[messageId] = response;
          return response;
        }),
        catchError((error) => {
          console.error('Error checking authorization:', error);
          return of(false);
        })
      );
    }
}
