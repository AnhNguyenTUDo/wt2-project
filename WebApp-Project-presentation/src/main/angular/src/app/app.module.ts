import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BasicAuthService } from './auth/basic-auth.service';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AngularComponent } from './angular/angular.component';
import { AuthComponent } from './auth/auth.component';
import { RegisterComponent } from './register/register.component';
import { CreateMessageComponent } from './angular/create-message/create-message.component';
import { MessageListComponent } from './angular/message-list/message-list.component';
import { EnvironmentBadgeComponent } from './shared/environment-badge/environment-badge.component';
import { LoginComponent } from './auth/login/login.component';



@NgModule({
  declarations: [
    AppComponent,
    AngularComponent,
    AuthComponent,
    RegisterComponent,
    CreateMessageComponent,
    MessageListComponent,
    EnvironmentBadgeComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [BasicAuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }
