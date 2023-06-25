import { Component } from '@angular/core';
import { CreateMessageComponent } from '../../angular/create-message/create-message.component';
import { AuthMessageService } from '../auth-message.service';

@Component({
  selector: 'app-create-message-auth',
  templateUrl: '../../angular/create-message/create-message.component.html',
  styleUrls: ['../../angular/create-message/create-message.component.sass']
})
export class CreateMessageAuthComponent extends CreateMessageComponent {

  constructor(messageService: AuthMessageService) {
    super(messageService);
  }
}
