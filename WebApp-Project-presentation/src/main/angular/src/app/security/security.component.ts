import { Component } from '@angular/core';
import { SecurityNewsService } from './security-news.service';
/////////////////////////////////////////////////////////////////
import { SecurityMessagesService } from './security-messages.service';
import { AngularComponent } from '../angular/angular.component';

@Component({
  selector: 'wt2-security',
  templateUrl: './security.component.html',
  styleUrls: ['./security.component.sass'],
  providers: [SecurityNewsService,
  ///////////////////////////////////////////////
              SecurityMessagesService]
})
export class SecurityComponent extends AngularComponent {

  constructor(newsService: SecurityNewsService,
              //////////////////////////////////////////////
              messagesService: SecurityMessagesService) {
    super(newsService,
          messagesService);
  }
}
