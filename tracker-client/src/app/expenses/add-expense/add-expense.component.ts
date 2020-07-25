import { Component, OnInit } from '@angular/core';

import { NgForm } from '@angular/forms';
import { Router } from '@angular/router'

import { Expense } from '../shared/expense.model';
import { ToastrService } from 'ngx-toastr';

import { HttpService } from '../../http.service'

@Component({
  selector: 'app-add-expense',
  templateUrl: './add-expense.component.html',
  styleUrls: ['./add-expense.component.scss']
})
export class AddExpenseComponent implements OnInit {

  categories: any
  expense: Expense;

  constructor(private _http: HttpService, private _router: Router, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.resetForm();
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


  showSuccess() {
    this.toastr.success('Expense added successfully');
  }

  showError() {
    this.toastr.error("Could not add expense. Try again");
  }

  resetForm(form?: NgForm) {
    if (form != null)
      form.reset();
      this.expense = {
        expense_id: '',
        category: '',
        description: '', 
        amount: 0, 
        comment: '',
        currency: '',
        expense_date: ''
      }
  }

  OnSubmit(form: NgForm) {
    console.log("On submit ");
    this._http.saveExpense(form.value)
      .subscribe(
        res => {
          console.log(res)
          this.showSuccess()
          this._router.navigate(['/expenses'])
        },
        err => {
          console.log(err)
          this.showError();
        }
      );
  }

}
