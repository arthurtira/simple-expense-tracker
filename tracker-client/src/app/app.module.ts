import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { ExpensesComponent } from './expenses/expenses.component';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { UserComponent } from './user/user.component';
import { SignInComponent } from './user/sign-in/sign-in.component';
import { RegisterComponent } from './user/register/register.component';

import { FormsModule} from '@angular/forms';

import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {AuthGuard } from './auth.guard'
import { TokenInterceptorService } from './token-interceptor.service'
import { UserService } from './user/shared/user.service';
import { AddExpenseComponent } from './expenses/add-expense/add-expense.component'

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { EditExpenseComponent } from './expenses/edit-expense/edit-expense.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ExpensesComponent,
    UserComponent,
    SignInComponent,
    RegisterComponent,
    AddExpenseComponent,
    EditExpenseComponent
  ],
  imports: [
    BrowserModule,
    FontAwesomeModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ToastrModule.forRoot({
      timeOut: 5000,
      positionClass: 'toast-top-center',
      preventDuplicates: true,
    }),
    BrowserAnimationsModule
  ],
  providers: [UserService, AuthGuard , {
    provide: HTTP_INTERCEPTORS, 
    useClass: TokenInterceptorService, 
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
