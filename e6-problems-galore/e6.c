#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define NDIGITS 40

void printNum(char* n) {
	int printed_a_digit = 0;
	
	for (int i=NDIGITS-1; i>=0; i--) {
		if (!printed_a_digit && (n[i] == 0))
			continue;
		else {
			putchar('0'+n[i]);
			printed_a_digit = 1;
		}
	}
	if (!printed_a_digit)
		putchar('0');
	putchar('\n');
}


int hiDigit(char* n) {
	int hi = 0;
	for (int i=0; i<NDIGITS; i++)
		if (n[i]>0)
			hi=i;
	return hi;
}

void addTo(char* b, long n) {
	for(int d=0; d<NDIGITS; d++) {
		if (n==0 && b[d]<10)
			break;
		int r = n%10;
		n/=10;
		b[d] += r;
		while (b[d]>=10) {
			b[d] -= 10;
			b[d+1]++;
		}
	}
}

void moveNum(char* to, char* from) {
	bcopy(from, to, NDIGITS);
}

void multiplyBy(char* dest, char* m) {
	char partial[NDIGITS];
	bzero(partial, NDIGITS);
	for (int di=0; di<=hiDigit(dest); di++) {
		for (int mi=0; mi<=hiDigit(m); mi++){
			long p = dest[di] * m[mi];
			addTo(partial+di+mi, p);
		}
	}
	moveNum(dest, partial);
}

void subtractFrom(char* dest, char* n) {
	for (int i=0; i<NDIGITS; i++)
		dest[i] -= n[i];
	for (int i=0; i<NDIGITS; i++) {
		while (dest[i]<0) {
			dest[i+1]--;
			dest[i] += 10;
		}
	}
}

void square(char* n) {
	char product[NDIGITS];
	bzero(product, NDIGITS);
	moveNum(product, n);
	multiplyBy(product, n);
	moveNum(n, product);
}

int main(int ac, char** av) {
	int n = atoi(av[1]);
	char sum_n[NDIGITS];
	bzero(sum_n, NDIGITS);
	char sum_n2[NDIGITS];
	bzero(sum_n2, NDIGITS);
		
	for (long i=1; i<=n; i++) {
		addTo(sum_n, i);
		addTo(sum_n2, i*i);
	}
	square(sum_n);
	subtractFrom(sum_n, sum_n2);
	printNum(sum_n);
}




