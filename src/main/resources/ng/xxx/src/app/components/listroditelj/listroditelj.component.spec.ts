import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListroditeljComponent } from './listroditelj.component';

describe('ListroditeljComponent', () => {
  let component: ListroditeljComponent;
  let fixture: ComponentFixture<ListroditeljComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListroditeljComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListroditeljComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
