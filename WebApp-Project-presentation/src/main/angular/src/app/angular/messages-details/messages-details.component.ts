// import { Component } from '@angular/core';
//
// @Component({
//   selector: 'wt2-messages-details',
//   templateUrl: './messages-details.component.html',
//   styleUrls: ['./messages-details.component.sass']
// })
// export class MessagesDetailsComponent {
//
// }

import { Component, Input } from '@angular/core';
import { Messages } from '../../messages';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'wt2-messages-details',
  templateUrl: './messages-details.component.html',
  styleUrls: ['./messages-details.component.sass']
})
export class MessagesDetailsComponent {

  @Input()
  public messages: Messages | null = null;

  @Input()
  public allowHtmlContent: boolean;

  constructor(private domSanitizer: DomSanitizer) {
    this.allowHtmlContent = false;
  }

  getTrustedHtml(value: string): SafeHtml {
    return this.domSanitizer.bypassSecurityTrustHtml(value);
  }
}

