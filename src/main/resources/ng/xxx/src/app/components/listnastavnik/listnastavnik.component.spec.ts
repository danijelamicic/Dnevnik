import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListnastavnikComponent } from './listnastavnik.component';

describe('ListnastavnikComponent', () => {
  let component: ListnastavnikComponent;
  let fixture: ComponentFixture<ListnastavnikComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListnastavnikComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListnastavnikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
