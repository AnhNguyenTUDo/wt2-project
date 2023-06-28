import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
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
export class AuthComponent extends AngularComponent implements OnInit {

    @Output()
    editToggleEvent = new EventEmitter<boolean>();
    @Output()
    deleteToggleEvent = new EventEmitter<boolean>();

    public showDeleteButtons: boolean = false;
    public showEditButtons: boolean = false;

  constructor(private http: HttpClient,
              private authMessageService: AuthMessageService,
              public authService: BasicAuthService) {
  super(authMessageService);}

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

   toggleEditButtons(e: Event){
    this.showEditButtons = !this.showEditButtons;
    this.editToggleEvent.emit(this.showEditButtons);
   }
   toggleDeleteButtons(e: Event) {
    this.showDeleteButtons = !this.showDeleteButtons;
    this.deleteToggleEvent.emit(this.showDeleteButtons);
   }
}

