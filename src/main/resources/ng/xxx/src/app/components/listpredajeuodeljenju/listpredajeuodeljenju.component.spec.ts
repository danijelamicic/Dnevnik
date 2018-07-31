import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListpredajeuodeljenjuComponent } from './listpredajeuodeljenju.component';

describe('ListpredajeuodeljenjuComponent', () => {
  let component: ListpredajeuodeljenjuComponent;
  let fixture: ComponentFixture<ListpredajeuodeljenjuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListpredajeuodeljenjuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListpredajeuodeljenjuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
