import { Component } from '@angular/core';
import { RegisterService } from './register.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.sass'],
  providers: [RegisterService]
})
export class RegisterComponent {
  public username: string = '';
      public password: string = '';
      public errorMessage: string | null = null;

    constructor(private registerService: RegisterService) { }

      register() {
        this.errorMessage = null;
        if (this.canRegister()) {
          this.registerService.registerUser(this.username,this.password).subscribe({
            next: (response: any) => {
                    console.log('Registration successful:', response);
                  },
                  error: (error: any) => {
                    console.error('Registration failed:', error);
                    this.errorMessage = 'Failed to register';
                  }
          });
        }
      }

      canRegister(): boolean {
        return this.username.trim() !== '' && this.password.trim() !== '';
      }
}


