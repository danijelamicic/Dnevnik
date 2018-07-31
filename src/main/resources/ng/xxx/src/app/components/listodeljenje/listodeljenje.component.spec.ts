import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListodeljenjeComponent } from './listodeljenje.component';

describe('ListodeljenjeComponent', () => {
  let component: ListodeljenjeComponent;
  let fixture: ComponentFixture<ListodeljenjeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListodeljenjeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListodeljenjeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
