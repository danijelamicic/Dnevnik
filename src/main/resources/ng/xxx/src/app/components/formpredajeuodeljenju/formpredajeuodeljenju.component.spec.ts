import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormpredajeuodeljenjuComponent } from './formpredajeuodeljenju.component';

describe('FormpredajeuodeljenjuComponent', () => {
  let component: FormpredajeuodeljenjuComponent;
  let fixture: ComponentFixture<FormpredajeuodeljenjuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormpredajeuodeljenjuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormpredajeuodeljenjuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
