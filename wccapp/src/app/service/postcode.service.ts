import {Injectable} from '@angular/core';
import {environment} from '../../environment/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import { Postcodelatlng } from '../model/postcodelatlng';
import { PostcodelatlngSearchModel } from '../model/postcodelatlng-search-model';
import { PostcodeDistanceModel } from '../model/postcode-distance-model';
import { Result } from '../model/result';
import { PostcodeDistanceLogs } from '../model/postcode-distance-logs';
import { PostcodeDistanceLogsSearchModel } from '../model/postcode-distance-logs-search-model';

@Injectable({
  providedIn: 'root'
})
export class PostcodeService {

  private apiServerUrl = environment.apiBaseUrl + '/wcc/postcodelatlng';


  constructor(private http: HttpClient) {
  }

  public findPostcodelatlngs(searchModel: PostcodelatlngSearchModel): Observable<Result<Postcodelatlng[]>> {
    return this.http.post<Result<Postcodelatlng[]>>(`${this.apiServerUrl}/find`, searchModel);
  }

  public getPostcodelatlng(postcode: string): Observable<Postcodelatlng> {
    return this.http.get<Postcodelatlng>(`${this.apiServerUrl}/findByPostcode`, {
      params: { postcode }
    });
  }

  public calculateDistance(firstLocation: number, secondLocation: number, userId: string): Observable<PostcodeDistanceModel> {
    return this.http.post<PostcodeDistanceModel>(`${this.apiServerUrl}/calculate-distance`, null, {
        params: {
            firstLocation: firstLocation.toString(),
            secondLocation: secondLocation.toString(),
            userId: userId
        }
    });
  }

  public addPostcodelatlng(postcodelatlng: Postcodelatlng): Observable<boolean> {
    return this.http.post<boolean>(`${this.apiServerUrl}/add`, postcodelatlng);
  }

  public updatePostcodelatlng(postcodelatlng: Postcodelatlng): Observable<Postcodelatlng> {
    return this.http.post<Postcodelatlng>(`${this.apiServerUrl}/update`, postcodelatlng);
  }

  public findPostcodeDistanceLogs(logsSearchModel: PostcodeDistanceLogsSearchModel): Observable<Result<PostcodeDistanceLogs[]>> {
    return this.http.post<Result<PostcodeDistanceLogs[]>>(`${this.apiServerUrl}/find-logs`, logsSearchModel);
  }

}
