import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Customer } from '../../../model/account-info';
import { CustomerService } from '../../../service/customer.service';
import { PostcodelatlngSearchModel } from '../../../model/postcodelatlng-search-model';
import { Postcodelatlng } from '../../../model/postcodelatlng';
import { PostcodeService } from '../../../service/postcode.service';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { PostcodeDistanceModel } from './../../../model/postcode-distance-model';
import { NzMessageService } from 'ng-zorro-antd/message';
import { isEqual } from 'lodash';
import { PostcodeDistanceLogs } from './../../../model/postcode-distance-logs';
import { PostcodeDistanceLogsSearchModel } from '../../../model/postcode-distance-logs-search-model';

@Component({
  selector: 'app-uk-postcode',
  templateUrl: './uk-postcode.component.html',
  styleUrl: './uk-postcode.component.less'
})
export class UkPostcodeComponent {

  customer: Customer;
  postcodelatlngs: Postcodelatlng[] = [];
  searchModel: PostcodelatlngSearchModel = {
    id: null,
    postcode: '',
    postcode2: '',
    latitude: null,
    longtitude: null,
    offset: 0,
    maxResult: 10,
    order: 'id',
    orderFlow: 'ASC'
  };
  totalCount: number = 0; 
  isLoading: boolean;

  isDistanceLoading: boolean;
  selectedId1: number;
  selectedId2: number;
  postcodeDistance: PostcodeDistanceModel;
  formattedDistanceData: any[] = [];

  isVisible: boolean = false;
  isConfirmLoading: boolean = false;
  updatePostcodeModel: Postcodelatlng;
  updatePostcodeForm: FormGroup;

  postcodeDistanceLogs: PostcodeDistanceLogs[] = [];
  totalLogs: number;
  drawerVisible: boolean;
  logsSearchModel: PostcodeDistanceLogsSearchModel = {
    userId: null,
    offset: 0,
    maxResult: 10,
    order: 'id',
    orderFlow: 'ASC'
  };
  isLogsLoading: boolean;

  constructor(
    private postcodeService: PostcodeService,
    private customerService: CustomerService, 
    private router: Router, 
    private fb: FormBuilder,
    private message: NzMessageService,
  ) {
  }

  ngOnInit() {
    const userId = JSON.parse(localStorage.getItem('userId') || 'null');

    if (userId) {
      this.customerService.getCustomer(userId).subscribe(
        (resp: Customer) => {
          this.customer = resp;
          this.searchModel.order = 'id';
          this.searchModel.orderFlow = 'ASC';
          this.searchModel.offset = 0;
          this.searchModel.maxResult = 10;
          this.initAllPostcodelatlngs();
          this.initForm();
        },
        (error) => {
          console.error('Error fetching customer data', error);
          this.message.error('Error fetching customer data!');
        }
      );
    } else {
      console.warn('No userId found in localStorage');
    }
  }

  initAllPostcodelatlngs(): void {
    this.selectedId1 = null;
    this.selectedId2 = null;
    this.postcodeDistance = null;
    this.isLoading = true;
    this.postcodeService.findPostcodelatlngs(this.searchModel).subscribe(
      resp => {
        this.postcodelatlngs = resp.data;
        this.totalCount = resp.total; 
        this.isLoading = false;
      },
      error => {
        console.error('Error occurred when retrieving data', error);
        this.message.error('Error occurred when retrieving data!');
      });
  }

  initForm(): void {
    this.updatePostcodeForm = this.fb.group({
      id: [{ value: null, disabled: true }, Validators.required],
      postcode: ['', Validators.required],
      latitude: ['', Validators.required],
      longitude: ['', Validators.required],
    });
  }

  calculateDistance(): void {
    if (this.selectedId1 && this.selectedId2) {
      this.isDistanceLoading = true;
      this.postcodeService.calculateDistance(this.selectedId1, this.selectedId2, this.customer.userId).subscribe(
        resp => {
          this.postcodeDistance = resp;
          this.prepareTableData(this.postcodeDistance);
          this.isDistanceLoading = false;
       },
        error => {
          console.error('Error occurred when calculating distance between two coordinates', error);
          this.message.error('Error occurred when calculating distance between two coordinates!');
        });
    } else {
      this.message.error('Please select at least two locations for calculating distance!');
    }
  }

  prepareTableData(postcodeDistance: PostcodeDistanceModel): void {
    this.formattedDistanceData = [
      {
        postcode: postcodeDistance.postcode1,
        latitude: postcodeDistance.latitude1,
        longitude: postcodeDistance.longitude1
      },
      {
        postcode: postcodeDistance.postcode2,
        latitude: postcodeDistance.latitude2,
        longitude: postcodeDistance.longitude2
      },
      {
        postcode: postcodeDistance.distance,  
        latitude: '',  
        longitude: ''  
      }
    ];
  }

  onQueryParamsChange(params: NzTableQueryParams, isLog?: boolean): void {
    const { pageSize, pageIndex, sort, filter } = params;
    const currentSort = sort.find(item => item.value !== null);

    if (isLog) {
      this.logsSearchModel.order = (currentSort && currentSort.key) || null;
      this.logsSearchModel.orderFlow = currentSort ? currentSort.value === 'ascend' ? 'ASC' : 'DESC' : null;
      this.findPostcodeDistanceLogs();
    } else {
      this.searchModel.order = (currentSort && currentSort.key) || null;
      this.searchModel.orderFlow = currentSort ? currentSort.value === 'ascend' ? 'ASC' : 'DESC' : null;
      this.initAllPostcodelatlngs();
    }
  }

  onPageChange(pageIndex: number, isLog?: boolean): void {
    if (isLog) {
      this.logsSearchModel.offset = (pageIndex - 1) * this.logsSearchModel.maxResult;
      this.findPostcodeDistanceLogs();
    } else {
      this.searchModel.offset = (pageIndex - 1) * this.searchModel.maxResult;
      this.initAllPostcodelatlngs();
    }
  }

  onPageSizeChange(pageSize: number, isLog?: boolean): void {
    if (isLog) {
      this.logsSearchModel.maxResult = pageSize;
      this.findPostcodeDistanceLogs();
    } else {
      this.searchModel.maxResult = pageSize;
      this.initAllPostcodelatlngs();
    }
  }

  onReset(isLog?: boolean): void {
    if (isLog) {
      this.logsSearchModel.order = 'updateDate';
      this.logsSearchModel.orderFlow = 'DESC';
      this.logsSearchModel.offset = 0;
      this.logsSearchModel.maxResult = 10;
      this.findPostcodeDistanceLogs();
    } else {
      this.searchModel.order = 'id';
      this.searchModel.orderFlow = 'ASC';
      this.searchModel.offset = 0;
      this.searchModel.maxResult = 10;
      this.initAllPostcodelatlngs();
    }
  }

  logout(): void {
    this.customerService.logout().subscribe(
      resp => {
        localStorage.removeItem('userId');
        this.router.navigate(['account-opening/account-info']);
      },
      error => {
        console.error('Error during logout', error);
      }
    );
  }

  showModal(selectedLocation: Postcodelatlng): void {
    this.isVisible = true;
    this.updatePostcodeModel = { ...selectedLocation }; 

    this.updatePostcodeForm.patchValue({
      id: selectedLocation.id,
      postcode: selectedLocation.postcode,
      latitude: selectedLocation.latitude,
      longitude: selectedLocation.longitude
    });
  }

  closeModal(): void {
    this.isVisible = false;
  }

  updatePostcodelatlng(): void {
    let updateModel: Postcodelatlng = {
      id: this.updatePostcodeForm.get('id').value,
      postcode: this.updatePostcodeForm.get('postcode').value,
      latitude: this.updatePostcodeForm.get('latitude').value,
      longitude: this.updatePostcodeForm.get('longitude').value,
    };
    this.isConfirmLoading = true;
    
    if (this.updatePostcodeForm.valid) {
      if (!isEqual(updateModel, this.updatePostcodeModel)) {
        this.postcodeService.updatePostcodelatlng(updateModel).subscribe(
          resp => {
            this.message.success('Record has been successfully updated!');
            this.isConfirmLoading = false;
            this.isVisible = false;
            this.initAllPostcodelatlngs();
          },
          error => {
            this.isConfirmLoading = false;
            console.error('Error occurred when updating postcode', error);
            this.message.error('Error occurred when updating postcode!');
          });
      }
    } else {
      this.isConfirmLoading = false;
    }
  }

  downloadJson(): void {
    const jsonString = JSON.stringify(this.postcodeDistance, null, 2);
    const blob = new Blob([jsonString], { type: 'application/json' });
    const a = document.createElement('a');
    a.href = URL.createObjectURL(blob);
    a.download = 'distance-data.json';
    a.click();
    URL.revokeObjectURL(a.href);
  }

  openDrawer(): void {
    this.drawerVisible = true;
    this.logsSearchModel.order = 'updateDate';
    this.logsSearchModel.orderFlow = 'DESC';
    this.logsSearchModel.offset = 0;
    this.logsSearchModel.maxResult = 10;
    this.logsSearchModel.userId = this.customer.userId;
    this.findPostcodeDistanceLogs();
  }

  closeDrawer(): void {
    this.drawerVisible = false;
  }

  findPostcodeDistanceLogs(): void {
    this.isLogsLoading = true;
    this.postcodeService.findPostcodeDistanceLogs(this.logsSearchModel).subscribe(
      resp => {
        this.postcodeDistanceLogs = resp.data;
        this.totalLogs = resp.total;
        this.isLogsLoading = false;
      },
      error => {
        console.error('Error occurred when retrieving postcode distance logs', error);
        this.message.error('Error occurred when retrieving postcode distance logs!');
      });
  }


}
