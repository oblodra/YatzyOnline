package com.yatzy.helpclasses;

public class ArrayLinkMergesort {
	Link[] array;
	int size;
	int sortAfter;
	
	public ArrayLinkMergesort(Link[] array, int size, int sortAfter) {
		this.array = array;
		this.size = size;
		this.sortAfter = sortAfter;
	}
	
	public void sort() {
		Link[] workspace = new Link[size];
		recMergeSort(workspace, 0, size-1);
		
	}
	
	private void recMergeSort(Link[] workspace, int lowerBound, int upperBound) {
		if (lowerBound == upperBound) {
			return;
		}
		else {
			int mid = (lowerBound+upperBound) / 2;
			recMergeSort(workspace, lowerBound, mid);
			recMergeSort(workspace, mid+1, upperBound);
			merge(workspace, lowerBound, mid+1, upperBound);
		}
			
	}
	
	private void merge(Link[] workspace, int lowerPtr, int upperPtr, int upperBound) {
		int j = 0;
		int lowerBound = lowerPtr;
		int mid = upperPtr - 1;
		int n  = upperBound - lowerBound + 1;
		
		while (lowerPtr <= mid && upperPtr <= upperBound) {
			if (this.sortAfter == 0) {
				
				if (array[lowerPtr].getMatch() < array[upperPtr].getMatch()) {
					workspace[j++] = array[upperPtr++];
				}
				else {
					workspace[j++] = array[lowerPtr++];
				}
				
			} else if (this.sortAfter == 1) {
				if (array[lowerPtr].getMaxPoints() < array[upperPtr].getMaxPoints()) {
					workspace[j++] = array[upperPtr++];
				}
				else {
					workspace[j++] = array[lowerPtr++];
				}
			} else if (sortAfter == 2) {
				if (array[lowerPtr].getCurrentPoints() < array[upperPtr].getCurrentPoints()) {
					workspace[j++] = array[upperPtr++];
				}
				else {
					workspace[j++] = array[lowerPtr++];
				}
			}
			
		}
		
		while (lowerPtr <= mid) {
			workspace[j++] = array[lowerPtr++];
		}
		
		while (upperPtr <= upperBound) {
			workspace[j++] = array[upperPtr++];
		}
		
		for (j=0; j<n; j++) {
			array[lowerBound+j] = workspace[j];
		}
	}
	
	/*// sorts normally
	private void merge(Link[] workspace, int lowerPtr, int upperPtr, int upperBound) {
		int j = 0;
		int lowerBound = lowerPtr;
		int mid = upperPtr - 1;
		int n  = upperBound - lowerBound + 1;
		
		while (lowerPtr <= mid && upperPtr <= upperBound) {
			if (this.sortAfter == 0) {
				if (array[lowerPtr].getMatch() < array[upperPtr].getMatch()) {
					workspace[j++] = array[lowerPtr++];
				}
				else {
					workspace[j++] = array[upperPtr++];
				}
			} else if (this.sortAfter == 1) {
				if (array[lowerPtr].getMaxPoints() < array[upperPtr].getMaxPoints()) {
					workspace[j++] = array[lowerPtr++];
				}
				else {
					workspace[j++] = array[upperPtr++];
				}
			} else if (sortAfter == 2) {
				if (array[lowerPtr].getCurrentPoints() < array[upperPtr].getCurrentPoints()) {
					workspace[j++] = array[lowerPtr++];
				}
				else {
					workspace[j++] = array[upperPtr++];
				}
			}
			
		}
		
		while (lowerPtr <= mid) {
			workspace[j++] = array[lowerPtr++];
		}
		
		while (upperPtr <= upperBound) {
			workspace[j++] = array[upperPtr++];
		}
		
		for (j=0; j<n; j++) {
			array[lowerBound+j] = workspace[j];
		}
	}	
	*/
	
	public void display() {
		for (int i=0; i<size; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();	
	}
	
	public Link[] getArray() {
		return this.array;
	}
	
	
	
	
	
	
	
	
}
