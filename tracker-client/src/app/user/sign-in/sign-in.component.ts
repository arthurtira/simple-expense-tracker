import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router'
import { UserService } from '../shared/user.service';
import { Login } from '../shared/login.model';



@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent implements OnInit {

  user: Login;

  constructor(private userService: UserService, 
    private router: Router) { }

  ngOnInit() {
    this.resetForm();
  }

  resetForm(form?: NgForm) {
    if (form != null)
      form.reset();
      this.user = {
        username: '',
        password: ''
      }
  }

  OnSubmit(form: NgForm) {
    console.log("On submit ");
    this.userService.login(form.value)
      .subscribe(
        res => {
          console.log(res)
          localStorage.setItem('token', res.jwt)
          this.router.navigate(['/expenses'])
        },
        err => console.log(err)
      );
  }


}
