#include <stdio.h>
#include <limits.h> 
#include <string.h>

int wholeSum = INT_MIN;
int m,n;
int number;
int counter=0;

//Prints the array 
void countSum(int a[number][4],int s, int matrix[m][n]) { 
    int testM[m][n];
    memset(testM, 0, sizeof(testM[0][0]) * m * n);
    int sum = 0;
    counter++;
    //printf("permutations------%d\n", counter);
    for (int i=0; i<s; i++){
        //sum+=sumQuery(matrix, a[i][0], a[i][1], a[i][2], a[i][3]);
        //printf("sum is = %d\n", sum);
        
        if (a[i][0] == a[i][2]){
            if (a[i][1]>a[i][3]){
                //printf("%d %d\n", a[i][3], a[i][1]);
                for (int j =a[i][1]; j > a[i][3]-1; j--){
                    if(testM[a[i][0]][j] == 1){
                        break;
                    }
                   testM[a[i][0]][j] = 1;
                   sum+=matrix[a[i][0]][j];
               }
                  
            } else{
               // printf("%d %d\n", a[i][3], a[i][1]);
                for (int j =a[i][1]; j < a[i][3]+1; j++){
                    if(testM[a[i][0]][j] == 1){
                        break;
                    }
                   testM[a[i][0]][j] = 1;   
                   sum+=matrix[a[i][0]][j];  
                }
            }      
        } else{
            if (a[i][0]>a[i][2]){
                for (int j =a[i][0]; j > a[i][2]-1; j--){
                    if(testM[j][a[i][1]] == 1){
                        break;
                    }
                   testM[j][a[i][1]] = 1;
                   sum+=matrix[j][a[i][1]];
               }
                  
            } else{
                for (int j =a[i][0]; j < a[i][2]+1; j++){
                    if(testM[j][a[i][1]] == 1){
                        break;
                    }
                   testM[j][a[i][1]] = 1; 
                   sum+=matrix[j][a[i][1]];    
                }
            }
        }
    }
    /*
    printf("\n");
    for (int i = 0; i < m; i++){
        for (int j = 0; j < n; j++){
            printf("%d ", testM[i][j]);                 
        }
        printf("\n");
    }
*/
    if (sum > wholeSum){
        wholeSum = sum;
    } 
} 
    

void permutations(int size, int s, int matrix[m][n], int perm[number][4]) 
{ 
    if (size == 1) 
    { 
        countSum(perm, s, matrix); 
        return; 
    } 
  
    for (int i=0; i<size; i++) 
    { 
        int tmp;
        permutations(size-1,s, matrix, perm); 
 
        if ((size%2)==0){
            for (int j = 0; j < 4; j++){
                tmp = perm[i][j];
                perm[i][j] = perm[size-1][j];
                perm[size-1][j] = tmp;
            }
        } else{
            for (int j = 0; j < 4; j++){            
              tmp = perm[0][j];
              perm[0][j] = perm[size-1][j];
              perm[size-1][j] = tmp;
          }
        }
    } 
} 


int main(int argc, char *argv[]){
/////////////////////////////////////////////////////////////
    //loading matrix
    
    int x;
    
    scanf("%d", &m);
    scanf("%d", &n);
    int matrix[m][n];
     /**
    for (int i = 0; i < n; i++){
        if ((scanf("%d", &x))==1){
            matrix[0][i] = x;
        } else{
            fprintf(stderr, "Error: Chybny vstup!\n");
            return 100;
        }       
    }
    
    for (int i = 1; i < m; i++){
        for (int j =0; j < n; j++){
            if ((scanf("%d", &x))==1){
                matrix[i][j] = x + matrix[i-1][j];
            } else{
                fprintf(stderr, "Error: Chybny vstup!\n");
                return 100;
            }    
        }   
    }
    
    for (int i = 0; i < m; i++){
        for (int j =1; j < n; j++){
            matrix[i][j] += matrix[i][j-1];  
        }   
    }
    */
        for (int i = 0; i < m; i++){
        for (int j = 0; j < n; j++){
            if ((scanf("%d", &x))==1){
                matrix[i][j] = x;
            } else{
                fprintf(stderr, "Error: Chybny vstup!\n");
                return 100;
            }
                 
             
        }
    }
//////////////////////////////////////////////////////////////
/*
        printf("\n");
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                printf("%d ", matrix[i][j]);                 
            }
            printf("\n");
        }
*/    
        //loading robots;
        scanf("%d", &number);
        int robots[number][4];
        for (int i = 0; i < number; i++){
            for (int j = 0; j < 4; j++){
                if ((scanf("%d", &x))==1){
                    robots[i][j] = x;
                } else{
                    fprintf(stderr, "Error: Chybny vstup!\n");
                    return 100;
                }   
            }
        }
        
        //printing
        /*
        for (int i = 0; i < number; i++){
            for (int j = 0; j < 4; j++){
                printf("%d ", robots[i][j]);
            }
            printf("\n");
        }
        */
        int testM[m][n];
        /*
        printf("%d this\n", testM[0][1]);
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                printf("%d ", testM[i][j]);
            }
            printf("\n");
        }*/
        
        //doing recursion
        permutations(number, number, matrix, robots);
        printf("%d\n", wholeSum);
        
}
