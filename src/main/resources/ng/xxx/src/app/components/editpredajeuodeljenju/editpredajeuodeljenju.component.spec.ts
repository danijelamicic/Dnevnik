import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditpredajeuodeljenjuComponent } from './editpredajeuodeljenju.component';

describe('EditpredajeuodeljenjuComponent', () => {
  let component: EditpredajeuodeljenjuComponent;
  let fixture: ComponentFixture<EditpredajeuodeljenjuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditpredajeuodeljenjuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditpredajeuodeljenjuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
