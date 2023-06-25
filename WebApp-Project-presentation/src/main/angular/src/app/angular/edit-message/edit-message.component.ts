import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from '../message.service';
import { Message } from '../../message';

@Component({
  selector: 'app-edit-message',
  templateUrl: './edit-message.component.html',
  styleUrls: ['./edit-message.component.sass']
})
export class EditMessageComponent implements OnInit {
  messageId: string = '';
  editedMessageContent: string = '';

  constructor(
    private route: ActivatedRoute,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.messageId = params['id'];
      this.loadMessage();
    });
  }

  loadMessage(): void {
    this.messageService.getMessage(this.messageId).subscribe(
      (message: Message) => {
        this.editedMessageContent = message.content;
      },
      (error) => {
        console.error('Error loading message:', error);
      }
    );
  }

  onSubmit(): void {
    if (this.editedMessageContent) {
      this.messageService.update(this.messageId, this.editedMessageContent).subscribe(
        (message) => {
          console.log('Message updated:', message);
        },
        (error) => {
          console.error('Error updating message:', error);
        }
      );
    }
  }
}
