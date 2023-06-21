// import { Component } from '@angular/core';
//
// @Component({
//   selector: 'wt2-create-messages',
//   templateUrl: './create-messages.component.html',
//   styleUrls: ['./create-messages.component.sass']
// })
// export class CreateMessagesComponent {
//
// }

import { Component, EventEmitter, Output } from '@angular/core';
import { MessagesService } from '../messages.service';

@Component({
  selector: 'wt2-create-messages',
  templateUrl: './create-messages.component.html',
  styleUrls: ['./create-messages.component.sass']
})
export class CreateMessagesComponent {

  @Output()
  public created = new EventEmitter();

//   public headline: string = "";
  public message: string = "";
  public errorMessage: string | null = null;

  constructor(private messagesService: MessagesService) { }

  public createMessages(e: Event): void {
    e.preventDefault();
    this.errorMessage = null;

    if (this.message.trim() != null) {
      this.messagesService.create(this.message).subscribe({
        next: () => {
          this.created.emit();
//           this.headline = '';
          this.message = '';
        },
        error: () => this.errorMessage = 'Could not create messages'
      });
    }
  }

  getCharsLeft(): number {
    return 255 - this.message.length;
  }

  get canCreate(): boolean {
    return this.getCharsLeft() > 0 && this.message.trim() !== '';
  }
}

