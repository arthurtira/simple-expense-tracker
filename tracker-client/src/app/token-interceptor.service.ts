import { Injectable, Injector } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http'
import { Observable } from 'rxjs';
import { UserService } from './user/shared/user.service';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor {

  constructor(private injector: Injector) { }
  
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let userService = this.injector.get(UserService)
    if(!!userService.getToken()) {
      let tokenizedReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${userService.getToken()}`
        }
      })
      return next.handle(tokenizedReq)
    }

    return next.handle(req)
    
  }

 
}
