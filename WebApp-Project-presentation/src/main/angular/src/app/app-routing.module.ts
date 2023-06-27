import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AngularComponent } from './angular/angular.component';
import { AuthComponent } from './auth/auth.component';
import { RegisterComponent } from './register/register.component';
// import { AppComponent } from './app.component';

const routes: Routes = [
  { path: 'auth', component: AuthComponent},
  { path: 'register', component: RegisterComponent},
//   { path: '', component: AppComponent}
  {
    path: '',
    redirectTo: '/angular',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
