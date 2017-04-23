import {Component} from '@angular/core';
import {HttpService} from './http.service';
import {RequestOptions, Response, Headers} from '@angular/http';
import {User, UserLink} from './user';
import {Injectable} from '@angular/core';


@Component({
    selector: "my-app",
    template: `<ul class="list-group" >
                 <li class="list-group-item" *ngFor="let user of userJson?.users">{{user.login}}
                 <input class="btn" type="button" (click)="changeStatus(user)" value="{{user.links[0].rel}}"/> 
                 </li>
              </ul>`,
    providers: [HttpService]
})

@Injectable()
export class AppComponent {
    userJson: User = undefined;

    constructor(private httpService: HttpService) {
    }

    ngOnInit() {
        this.httpService.getData().get('http://localhost:8080/users').subscribe(
            (data: Response) => {
                this.userJson = <User>data.json();
            }
        )
    }

    changeStatus(user: UserLink) {
       let headers: Headers = new Headers({'Content-Type': 'application/json'});
       let options: RequestOptions = new RequestOptions({headers: headers});

        this.httpService.getData().post(user.links[0].href, JSON.stringify({"login": user.login}), options)
            .subscribe(
                (data: Response) => {
                    this.ngOnInit();
                }
            );

    }
}

