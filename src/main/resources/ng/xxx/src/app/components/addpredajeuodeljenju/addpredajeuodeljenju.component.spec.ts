import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddpredajeuodeljenjuComponent } from './addpredajeuodeljenju.component';

describe('AddpredajeuodeljenjuComponent', () => {
  let component: AddpredajeuodeljenjuComponent;
  let fixture: ComponentFixture<AddpredajeuodeljenjuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddpredajeuodeljenjuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddpredajeuodeljenjuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
