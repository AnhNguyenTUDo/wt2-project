import { Component } from '@angular/core';
import { RegisterService } from './register.service';

@Component({
  selector: 'wt2-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.sass'],
  providers: [RegisterService]
})
export class RegisterComponent {

    public name: string = '';
    public password: string = '';
    public errorMessage: string;
//
  constructor(private registerService: RegisterService) { }
//
    register() {
      this.errorMessage = null;
      if (this.canRegister()) {
      //////////////////////////////////////{ name: this.name, password: this.password }
        this.registerService.registerUser(this.name,this.password).subscribe({
//           response => {
//                       // Handle successful registration
//                       console.log('Registration successful:', response);
//                     },
          error: () => this.errorMessage = 'Failed to register'
        });
      }
    }

    canRegister(): boolean {
      return this.name.trim() !== '' && this.password.trim() !== '';
    }
}
