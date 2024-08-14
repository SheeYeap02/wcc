import {Injectable} from '@angular/core';
import {environment} from '../../environment/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Customer} from '../model/account-info';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private apiServerUrl = environment.apiBaseUrl + '/wcc/customer';


  constructor(private http: HttpClient) {
  }

  public login(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(`${this.apiServerUrl}/login`, customer);
  }

  public logout(): Observable<string> {
    return this.http.post<string>(`${this.apiServerUrl}/logout`, {});
  }

  public addCustomer(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(`${this.apiServerUrl}/add`, customer);
  }

  public getCustomer(userId: string): Observable<Customer> {
    return this.http.get<Customer>(`${this.apiServerUrl}/findByUserId`, {
      params: { userId }
    });
  }

  public updateCustomer(customer: Customer): Observable<Customer> {
    return this.http.put<Customer>(`${this.apiServerUrl}/update`, customer);
  }

}
