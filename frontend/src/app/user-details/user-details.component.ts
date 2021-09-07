import { Component, OnInit } from '@angular/core';
import {UserService} from "../service/user.service";
import { ToastrService } from 'ngx-toastr';
import {Router} from '@angular/router';
@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {
  userData:any;
  constructor(private userService:UserService,
    private router:Router) { }

  ngOnInit() {
    let id = localStorage.getItem('id');
    this.userService.getUserById(+id)
    .subscribe( data => {
      this.userData = data;
    });
  }
  logout(){
    localStorage.removeItem("token");
    this.router.navigate(['login']);
  }
 

}
