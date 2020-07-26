import { Component, OnInit } from '@angular/core';
import { HttpService } from "../http.service"
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router'

import { ExpenseFilter } from './shared/filter.model'

import { NgForm } from '@angular/forms';
import { faMoneyBill } from '@fortawesome/free-solid-svg-icons';
import { faFilter } from '@fortawesome/free-solid-svg-icons';

import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-expenses',
  templateUrl: './expenses.component.html',
  styleUrls: ['./expenses.component.scss']
})
export class ExpensesComponent implements OnInit {

  expenses: any;
  filter: ExpenseFilter;
  categories: any
  faMoneyBill = faMoneyBill;
  faFilter = faFilter;
  search: boolean

  constructor(private _http: HttpService, 
            private _router: Router, 
            private _toastr: ToastrService) {  }

  ngOnInit() {
    this.resetForm();
    this.getExpenses();
    this.getCategories();  
    
  }

  resetForm(form?: NgForm) {
    if (form != null)
      form.reset();
      this.filter = {
        fromDate: '',
        toDate: '',
        category: 'All'
      }
  }

  OnSubmit(form: NgForm) {
    console.log("On submit has been clicked ");
      this._http.getFilteredExpenses(form.value).subscribe(res => {
        this.expenses = res;
        this.search = true;
      }, 
      err => {
        if(err instanceof HttpErrorResponse) {
          if(err.status === 403) {
            this._router.navigate(['/login'])
          }
        }
      })
  }

  showSuccess() {
    this._toastr.success('Expense deleted successfully');
  }

  showError() {
    this._toastr.error("Could not delete expense. Try again");
  }

  getExpenses() {
    this._http.getExpenses().subscribe(res => {
      this.expenses = res;
      this.search =false;
    }, 
    err => {
      if(err instanceof HttpErrorResponse) {
        if(err.status === 403) {
          this._router.navigate(['/login'])
        }
      }
    })
  }

  getCategories() {
    this._http.getExpenseCategories().subscribe(
      res => {
        console.log(res)
        this.categories = res;
      }, 
      err => {
        console.log(err)
      }
    );
  }

  removeExpense(expenseId: String) {
    console.log("Delete expense method clicked {} ")
    this._http.deleteExpense(expenseId).subscribe(
      res => {
        if(res.status === 204){
         this.getExpenses();
         this.showSuccess()
        }
      }, 
      err => {
        console.log(err)
        this.showError()
      }
    )
  }


}
