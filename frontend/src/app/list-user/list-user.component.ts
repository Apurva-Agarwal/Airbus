import { Component, OnInit,ViewChild } from '@angular/core';
import { Router } from "@angular/router";
import { UserService } from "../service/user.service";
import { User } from "../model/user.model";
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import {DataTableDirective} from 'angular-datatables';
@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})
export class ListUserComponent implements OnInit {
  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  p: number = 1;
  dtOptions: DataTables.Settings = {};
  users: User[];
  dtTrigger: Subject<any> = new Subject();


  constructor(private router: Router,
    private userService: UserService,
    private toastr: ToastrService) { }

  ngOnInit() {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 2,
      processing: true
    };
    this.getUsers();

  }
  getUsers() {
    this.userService.getUsers()
      .subscribe(data => {
        this.users = data;
        // this.dtTrigger.next();
        this.rerender();

      });
  }

  deleteUser(id: any): void {

    this.userService.deleteUser(id)
      .subscribe(data => {
        this.getUsers();
        this.toastr.success("User Deleted successfully");
      })
  };

  updateUser(id: number): void {
    localStorage.removeItem("editUserId");
    localStorage.setItem("editUserId", id.toString());
    this.router.navigate(['edit-user']);
  };

  addUser(): void {
    this.router.navigate(['add-user']);
  };

  logout() {
    localStorage.removeItem("token");
    this.router.navigate(['login']);
  }
  rerender(): void {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
    // Destroy the table first
    dtInstance.destroy();
    // Call the dtTrigger to rerender again
    this.dtTrigger.next();
      });}
    
    ngAfterViewInit(): void {
    this.dtTrigger.next();
     }
    
    ngOnDestroy(): void {
        this.dtTrigger.unsubscribe();
     }

}
