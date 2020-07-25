import { Component, OnInit } from '@angular/core';

import { NgForm } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router'

import { Expense } from '../shared/expense.model';

import { ToastrService } from 'ngx-toastr';


import { HttpService } from '../../http.service'

@Component({
  selector: 'app-edit-expense',
  templateUrl: './edit-expense.component.html',
  styleUrls: ['./edit-expense.component.scss']
})
export class EditExpenseComponent implements OnInit {

  public expenseId: string;
  categories: any
  expense: any;

  public id: string;

  constructor(private _http: HttpService, private _router: Router, 
    private route: ActivatedRoute, private toastr: ToastrService) { }

  ngOnInit(): void {

    this.expenseId = this.route.snapshot.paramMap.get('id');

    console.log("Expense id >>> " + this.expenseId)
    this._http.getExpenseById(this.expenseId).subscribe(
      res => {
        this.expense = res;
      },
      err => {
        console.log(err);
      }
    )

    this._http.getExpenseCategories().subscribe(
      res => {
        this.categories = res;
      }, 
      err => {
        console.log(err)
      }
    );
  }

  showSuccess() {
    this.toastr.success('Expense updated successfully');
  }

  showError() {
    this.toastr.error("Could not update expense. Try again");
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
        currency: ''
      }
  }

  OnSubmit(form: NgForm) {
    console.log("On submit edit expense ");
    this._http.editExpense(form.value, this.expenseId)
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
