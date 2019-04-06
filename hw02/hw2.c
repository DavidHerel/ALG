#include <stdio.h>
#include <limits.h> 
#include <string.h>

int wholeSum = INT_MIN;
int m,n;
int number;
int counter=0;

int sumQuery(int aux[m][n], int rbi, int rbj, int tli, int tlj){ 
    int res = aux[rbi][rbj]; 
  
    // Remove elements between (0, 0) and (tli-1, rbj) 
    if (tli > 0) 
       res = res - aux[tli-1][rbj]; 
  
    // Remove elements between (0, 0) and (rbi, tlj-1) 
    if (tlj > 0) 
       res = res - aux[rbi][tlj-1]; 
  
    // Add aux[tli-1][tlj-1] as elements between (0, 0) 
    // and (tli-1, tlj-1) are subtracted twice 
    if (tli > 0 && tlj > 0) 
       res = res + aux[tli-1][tlj-1]; 
  
    return res; 
} 

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
    

void heapPermutation(int a[number][4], int size, int s, int matrix[m][n]) 
{ 
    if (size == 1) 
    { 
        countSum(a, s, matrix); 
        return; 
    } 
  
    for (int i=0; i<size; i++) 
    { 
        int tmp;
        heapPermutation(a,size-1,s, matrix); 
 
        if (size%2==1){
            for (int j = 0; j < 4; j++){            
              tmp = a[0][j];
              a[0][j] = a[size-1][j];
              a[size-1][j] = tmp;
          }
        } else{
            for (int j = 0; j < 4; j++){
                tmp = a[i][j];
                a[i][j] = a[size-1][j];
                a[size-1][j] = tmp;
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

        printf("\n");
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                printf("%d ", matrix[i][j]);                 
            }
            printf("\n");
        }
    
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
        for (int i = 0; i < number; i++){
            for (int j = 0; j < 4; j++){
                printf("%d ", robots[i][j]);
            }
            printf("\n");
        }
        
        printf("\n"); 
        printf("testing empty array\n");
        int testM[m][n];
        printf("%d this\n", testM[0][1]);
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                printf("%d ", testM[i][j]);
            }
            printf("\n");
        }
        
        //doing recursion
        heapPermutation(robots, number, number, matrix);
        printf("sum is = %d\n", wholeSum);
        
}
