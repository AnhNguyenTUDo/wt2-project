import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BasicAuthService } from '../basic-auth.service';
import { AuthMessageService } from '../auth-message.service'
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent {

  @Input()
  public authService: BasicAuthService =  new BasicAuthService(this.http);
  constructor(private http: HttpClient) {}

  @Output()
  public loggedIn = new EventEmitter<void>();

//   constructor() {
//       this.authService = new BasicAuthService();
//     }

  public username: string = "";
  public password: string = "";
  public errorMessage: string | null = null;

  login(e: Event) {
    e.preventDefault();
    this.errorMessage = null;
    if (this.canLogin) {
      this.authService.login(this.username, this.password).subscribe({
        next: () => {this.loggedIn.emit();
                      console.log("is loggedin");},
        error: (error) => {this.errorMessage = 'Failed to login';
        console.log(this.errorMessage);
        console.log(error);}
      });
    }
  }

  get canLogin(): boolean {
    return this.username.trim() !== '' && this.password.trim() !== '';
  }
}
