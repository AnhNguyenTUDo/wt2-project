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
      this.authService.login(this.username, this.password).subscribe(//{
//////////////////////////DO NOT DELETE///////////////////////
//         next: () => {this.loggedIn.emit();
//                       console.log("is loggedin", this.loggedIn);},
//         error: (error: any) => {
//                             console.error('Login failed:', error);
//                             this.errorMessage = 'Failed to login!';
//                           }
        (loggedIn: boolean) => {
                if (loggedIn) {
                  this.loggedIn.emit();
                  console.log("is loggedin", this.loggedIn);
                } else {
                  // Handle login failure here
                  this.errorMessage = 'Invalid username or password';
                }
              },
              (error: any) => {
                console.error('Login failed:', error);
                this.errorMessage = 'Failed to login!';
              }
//           );
      //}
      );
    }
  }

  get canLogin(): boolean {
    return this.username.trim() !== '' && this.password.trim() !== '';
  }
}
