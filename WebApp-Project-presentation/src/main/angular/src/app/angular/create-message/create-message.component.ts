import { Component, EventEmitter, Output  } from '@angular/core';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-create-message',
  templateUrl: './create-message.component.html',
  styleUrls: ['./create-message.component.sass']
})
export class CreateMessageComponent {

  @Output()
  public created = new EventEmitter();

  public content: string = '';
  public errorMessage: string | null = null;

  constructor(private messageService: MessageService) { }

  public createMessage(e: Event): void {
    e.preventDefault();
    this.errorMessage = null;

    if(this.content.trim() != null) {
      this.messageService.create(this.content).subscribe({
        next: () => {
          this.created.emit();
          this.content = '';
        },
        error: () => this.errorMessage = 'Could not create message'
      });
      }
  }
  getCharsLeft(): number {
      return 255 - this.content.length;
    }

    get canCreate(): boolean {
      return this.getCharsLeft() > 0 && this.content.trim() !== '';
    }
//   onSubmit(): void {
//     if (this.content) {
//       this.messageService.create(this.content).subscribe(
//         (message) => {
//           console.log('Message created:', message);
//           // Optionally, perform any additional actions after creating the message
//         },
//         (error) => {
//           console.error('Error creating message:', error);
//           // Handle error case, display error message, etc.
//         }
//       );
//     }
//   }
}
