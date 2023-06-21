// import { Component } from '@angular/core';
//
// @Component({
//   selector: 'wt2-create-messages-security',
//   templateUrl: './create-messages-security.component.html',
//   styleUrls: ['./create-messages-security.component.sass']
// })
// export class CreateMessagesSecurityComponent {
//
// }

import { Component } from '@angular/core';
import { CreateMessagesComponent } from '../../angular/create-messages/create-messages.component';
import { SecurityMessagesService } from '../security-messages.service';

@Component({
  selector: 'wt2-create-messages-security',
  templateUrl: '../../angular/create-messages/create-messages.component.html',
  styleUrls: ['../../angular/create-messages/create-messages.component.sass']
})
export class CreateMessagesSecurityComponent extends CreateMessagesComponent {

  constructor(messagesService: SecurityMessagesService) {
    super(messagesService);
  }
}

