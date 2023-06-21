import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AngularComponent } from './angular/angular.component';
import { AuthComponent } from './auth/auth.component';
import { SecurityComponent } from './security/security.component';
import { CreateNewsComponent } from './angular/create-news/create-news.component';
import { NewsDetailsComponent } from './angular/news-details/news-details.component';
import { CreateNewsSecurityComponent } from './security/create-news-security/create-news-security.component';
import { LoginComponent } from './auth/login/login.component';
import { CreateNewsAuthComponent } from './auth/create-news-auth/create-news-auth.component';
import { NewsListComponent } from './angular/news-list/news-list.component';
import { EnvironmentBadgeComponent } from './shared/environment-badge/environment-badge.component';
import { CreateMessagesComponent } from './angular/create-messages/create-messages.component';
import { MessagesListComponent } from './angular/messages-list/messages-list.component';
import { MessagesDetailsComponent } from './angular/messages-details/messages-details.component';
import { CreateMessagesSecurityComponent } from './security/create-messages-security/create-messages-security.component';

@NgModule({
  declarations: [
    AppComponent,
    AngularComponent,
    AuthComponent,
    SecurityComponent,
    CreateNewsComponent,
    CreateNewsSecurityComponent,
    CreateNewsAuthComponent,
    NewsDetailsComponent,
    LoginComponent,
    NewsListComponent,
    EnvironmentBadgeComponent,
    CreateMessagesComponent,
    MessagesListComponent,
    MessagesDetailsComponent,
    CreateMessagesSecurityComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
