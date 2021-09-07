import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {Injectable} from "@angular/core";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let token = window.localStorage.getItem('token');
    if (token) {
      request = request.clone({
        setHeaders: {
          'Authorization': 'Bearer ' + token,
          'Accept': 'application/json',
        //   'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
        }
      });
    }
    return next.handle(request);
  }
}
