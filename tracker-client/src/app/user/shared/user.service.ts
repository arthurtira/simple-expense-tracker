import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Router } from '@angular/router'
import {Observable} from 'rxjs';
import { User } from './user.model';
import { Login } from './login.model';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  readonly rootUrl = 'http://localhost:8080/api/v1';
  
  constructor(private _http: HttpClient, private _router : Router) { }

  registerUser(user : User) : Observable<any>{
    const body: User = {
      username: user.username,
      password: user.password,
      email: user.email,
      firstName: user.firstName,
      lastName: user.lastName
    }
    return this._http.post(this.rootUrl + '/auth/register', body);
  }


  login(user: Login) : Observable<any> {
    const body: Login = {
      username: user.username,
      password: user.password
    }
    console.log(body)

    return this._http.post(this.rootUrl + "/auth/login", body);
  }

  loggedIn() {
    return !!localStorage.getItem('token')
  }

  getToken() {
    return localStorage.getItem('token');
  }

  logoutUser() {
    localStorage.removeItem('token');
    this._router.navigate(['/login'])
  }
    



}
