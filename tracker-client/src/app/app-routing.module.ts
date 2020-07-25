import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { ExpensesComponent } from './expenses/expenses.component';
import { AddExpenseComponent } from './expenses/add-expense/add-expense.component'
import { RegisterComponent } from './user/register/register.component';
import { SignInComponent } from './user/sign-in/sign-in.component';
import { AuthGuard } from './auth.guard';
import { EditExpenseComponent } from './expenses/edit-expense/edit-expense.component';



const routes: Routes = [
  { path: '', component: HomeComponent },             
  { path: 'expenses', component: ExpensesComponent , canActivate: [AuthGuard] },
  { path: 'expenses/add', component: AddExpenseComponent , canActivate: [AuthGuard] },
  { path: 'expenses/:id', component: EditExpenseComponent, canActivate: [AuthGuard] },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: SignInComponent }   
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
