// import { Component } from '@angular/core';
//
// @Component({
//   selector: 'wt2-messages-list',
//   templateUrl: './messages-list.component.html',
//   styleUrls: ['./messages-list.component.sass']
// })
// export class MessagesListComponent {
//
// }

import { Component, Input } from '@angular/core';
import { Messages } from '../../messages';

@Component({
  selector: 'wt2-messages-list',
  templateUrl: './messages-list.component.html',
  styleUrls: ['./messages-list.component.sass']
})
export class MessagesListComponent {

  @Input()
  public messages: Messages[] = [];

//   get reversedMessages(): Messages[] {
//     return this.messages.slice().reverse();
//   }
}
