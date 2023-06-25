import { Component, Input } from '@angular/core';
import { Message } from '../../message';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-message-detail',
  templateUrl: './message-detail.component.html',
  styleUrls: ['./message-detail.component.sass']
})
export class MessageDetailComponent {

  @Input()
  public message: Message | null  = null;

  @Input()
  public allowHtmlContent: boolean;

  constructor(private domSanitizer: DomSanitizer) {
    this.allowHtmlContent = false;
  }

  getTrustedHtml(value: string): SafeHtml {
    return this.domSanitizer.bypassSecurityTrustHtml(value);
  }
}


