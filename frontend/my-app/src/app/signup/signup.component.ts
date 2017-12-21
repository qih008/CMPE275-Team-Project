import { Component, OnInit, Injectable } from '@angular/core';
import { AngularFire, AuthProviders, AuthMethods } from 'angularfire2';
import { Router } from '@angular/router';
import { moveIn, fallIn } from '../router.animations';
import { HttpModule } from '@angular/http';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
  animations: [moveIn(), fallIn()],
  host: {'[@moveIn]': ''}
})
@Injectable()
export class SignupComponent implements OnInit {

  state: string = '';
  error: any;

  constructor(public af: AngularFire,private router: Router,private http: HttpModule) {

  }

  onSubmit(formData) {
    if(formData.valid) {
      console.log(formData.value);
      this.http.
      this.af.auth.createUser({
        email: formData.value.email,
        password: formData.value.password
      }).then(
        (success) => {
        this.router.navigate(['/members'])
      }).catch(
        (err) => {
        this.error = err;
      })
    }
  }

  ngOnInit() {
  }

}
