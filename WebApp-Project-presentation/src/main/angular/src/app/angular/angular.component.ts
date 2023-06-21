import { Component, OnInit } from '@angular/core';
import { NewsService } from './news.service';
import { News } from '../news';

import { MessagesService } from './messages.service';
import { Messages } from '../messages';

@Component({
  selector: 'wt2-angular',
  templateUrl: './angular.component.html',
  styleUrls: ['./angular.component.sass'],
  providers: [NewsService,
  /////////////////////////////////////
              MessagesService]
})
export class AngularComponent implements OnInit {

//   public latest: News | null = null;
//   public news: News[] = [];
//
//   constructor(protected newsService: NewsService) {
//   }
//
//   ngOnInit() {
//     this.load();
//   }
//
//   load(): void {
//     this.newsService.getNewest().subscribe({
//       next: news => this.latest = news,
//       error: console.error
//     });
//     this.newsService.getAll().subscribe({
//       next: news => this.news = news,
//       error: console.error
//     });
//   }

  public latest: News | null = null;
    public news: News[] = [];
    public messages: Messages[] = []


    constructor(protected newsService: NewsService,
                protected messagesService: MessagesService) {
    }

    ngOnInit() {
      this.load();
      this.loadMessages();
    }

    load(): void {
      this.newsService.getNewest().subscribe({
        next: news => this.latest = news,
        error: console.error
      });
      this.newsService.getAll().subscribe({
        next: news => this.news = news,
        error: console.error
      });
    }

    loadMessages(): void {

          this.messagesService.getAll().subscribe({
            next: messages => this.messages = messages,
            error: console.error
          });
        }
}
