import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-delete-message',
  templateUrl: './delete-message.component.html',
  styleUrls: ['./delete-message.component.sass']
})
export class DeleteMessageComponent implements OnInit {
  messageId: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.messageId = params['id'];
    });
  }

  onDelete(): void {
    this.messageService.delete(this.messageId).subscribe(
      () => {
        console.log('Message deleted successfully');
        this.router.navigate(['/messages']); // Redirect to messages list
      },
      (error) => {
        console.error('Error deleting message:', error);
      }
    );
  }
}
