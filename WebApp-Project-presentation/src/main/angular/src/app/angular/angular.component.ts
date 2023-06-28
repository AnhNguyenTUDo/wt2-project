import { Component, OnInit } from '@angular/core';
import { MessageService } from './message.service';
import { Message } from '../message'
@Component({
  selector: 'app-angular',
  templateUrl: './angular.component.html',
  styleUrls: ['./angular.component.sass'],
  providers: [MessageService]
})
export class AngularComponent implements OnInit {

  public messages: Message[] = [];

  constructor(protected messageService: MessageService) { }

  ngOnInit() {
    this.load();
    }

  load(): void {
    this.messageService.getAll().subscribe({
      next: messages => this.messages = messages,
      error: error => {
        console.error(error);
      }
    })
  }
}
