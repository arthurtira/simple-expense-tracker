import { Component, OnInit } from '@angular/core';
import { HttpService } from "../http.service"
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router'

import { ExpenseFilter } from './shared/filter.model'

import { NgForm } from '@angular/forms';
import { faMoneyBill } from '@fortawesome/free-solid-svg-icons';
import { faFilter } from '@fortawesome/free-solid-svg-icons';
import { Expense } from './shared/expense.model';


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

  constructor(private _http: HttpService, 
            private _router: Router) {  }

  ngOnInit() {
    this.resetForm();
    this._http.getExpenses().subscribe(res => {
      //console.log(res);
      this.expenses = res;
      console.log(this.expenses)
    }, 
    err => {
      if(err instanceof HttpErrorResponse) {
        if(err.status === 403) {
          this._router.navigate(['/login'])
        }
      }
    })
        

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
        console.log(res);
        this.expenses = res;
      }, 
      err => {
        if(err instanceof HttpErrorResponse) {
          if(err.status === 403) {
            this._router.navigate(['/login'])
          }
        }
      })
  }

  getExpenses() {
    this._http.getExpenses().subscribe(res => {
      this.expenses = res;
    });
  }

  removeExpense(expenseId: String) {
    console.log("Delete expense method clicked {} ")
    this._http.deleteExpense(expenseId).subscribe(
      res => {
        console.log(res);
        if(res.status === 204){
         this.getExpenses();
        }
      }, 
      err => {
        console.log(err)
      }
    )
  }


}
