import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Expense } from './expenses/shared/expense.model';
import { ExpenseFilter } from './expenses/shared/filter.model';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  
  readonly rootUrl = environment.api_url + "/expenses"
  constructor(private _http: HttpClient) { }

  getExpenses(){
    return this._http.get(this.rootUrl)
  } 

  getExpenseById(expenseId: String){
    return this._http.get(this.rootUrl +"/" + expenseId )
  } 

  getFilteredExpenses(filter: ExpenseFilter) : Observable<any>{
    const params: String = '?category='+ filter.category + '&fromDate='+ filter.fromDate + '&toDate=' + filter.toDate
    console.log("Params" + params)
    return this._http.get(this.rootUrl + params)
  } 

  deleteExpense(expenseId: String) : Observable<any>{
    return this._http.delete(this.rootUrl + "/" + expenseId)
  } 

  editExpense(expense: Expense, expenseId: string) : Observable<any>{
    const body: Expense = {
      category: expense.category,
      description: expense.description, 
      comment: expense.comment, 
      amount: expense.amount, 
      expense_id: expenseId, 
      currency: null,
      expense_date: null
    }
    return this._http.put(this.rootUrl , body)
  } 
  

  getExpenseCategories() {
    return this._http.get(this.rootUrl + "/categories")
  }

  saveExpense(expense: Expense) : Observable<any> {
    const body: Expense = {
      category: expense.category,
      description: expense.description, 
      comment: expense.comment, 
      amount: expense.amount, 
      expense_id: null, 
      currency: null,
      expense_date: null
    }
    console.log(body)
    return this._http.post(this.rootUrl, body);
  }
}
