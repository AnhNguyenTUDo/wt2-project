import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AngularComponent } from '../angular/angular.component';
import { BasicAuthService } from './basic-auth.service';
import { AuthMessageService } from './auth-message.service';
import { MessageService } from '../angular/message.service'

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.sass'],
  providers: [AuthMessageService, MessageService]
})
export class AuthComponent  extends AngularComponent implements OnInit {

  constructor(private http: HttpClient,
              private authMessageService: AuthMessageService,
              public authService: BasicAuthService) {
  super(authMessageService);}

//   ngOnInit(): void {
//       this.isLoggedIn = this.authService.isLoggedIn();
//     }

//     load(): void {
//       this.isLoggedIn = this.authService.isLoggedIn();
//     }

    logout(): void {

    this.authService.logout().subscribe(() => {
          this.authService.resetAdminStatus(); // Reset isAdmin to false
          this.messages = [];
        });

    }
    get isLoggedIn(): boolean {
        return this.authService.isLoggedIn;
        console.log("isLoggedIn" + this.isLoggedIn);
      }

    get loggedInUsername(): string | null {
      return this.authService.username;
    }
}

