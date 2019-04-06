#include <stdio.h>
#include <limits.h> 

int main(int argc, char *argv[]){
/////////////////////////////////////////////////////////////
    //loading matrix
    
    int m;
    int n;
    int x;
    
    scanf("%d", &m);
    scanf("%d", &n);
    int matrix[m][n];
     
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

    
    int subRight[m][n-1];
    

    int tmp;
    int index1, index2, index3;
    int maxBranch = INT_MIN;
    int maxMainBranch = INT_MIN;
    int third, second, first;
    third = first = second = -83646;
    int maxSUM = 0;
    
    //lets start with all radky - now vrati mi to nejvetsi max pro kazdy sloupec
    
    //WHAT NEED TO BE DONE - fill right matrix
    for (int k = 0; k < n-1; k++){
        for (int i = 0; i < m; i++){
            maxBranch = INT_MIN;
            maxSUM = 0;
            for (int j = k+1; j < n; j++){
                maxSUM += matrix[i][j];
                if (maxSUM> maxBranch){
                    maxBranch = maxSUM;
                }
            }
            subRight[i][k] = maxBranch;
        }

    }
    
    

    int subLeft[m][n-1];
    
    //fill left matrix
    for (int k = n-1; k > 0; k--){
        for (int i = 0; i < m; i++){
            maxBranch = INT_MIN;
            maxSUM = 0;
            for (int j = k-1; j >= 0; j--){
                maxSUM += matrix[i][j];
          
                if (maxSUM> maxBranch){
                    maxBranch = maxSUM;
                }
            }
      
            subLeft[i][k-1] = maxBranch;
        }

    }
    
    int subUpper[m-1][n];
    //fil upper matrix
    for (int k = m-1; k > 0; k--){
        for (int i = 0; i < n; i++){
            maxSUM =0;
            maxBranch = INT_MIN;
            for (int j = k-1; j >= 0; j--){
                maxSUM += matrix[j][i];
                if (maxSUM> maxBranch){
                    maxBranch = maxSUM;
                }
            }
            subUpper[k-1][i] = maxBranch;
        }

    }
 
    
    
    int subDown[m-1][n];
    //fil down matrix
    for (int k = 0; k < m-1; k++){
        for (int i = 0; i < n; i++){
            maxBranch = INT_MIN;
            maxSUM =0;
            for (int j = k+1; j < m; j++){
                maxSUM += matrix[j][i];
                if (maxSUM> maxBranch){
                    maxBranch = maxSUM;
                }
            }
            subDown[k][i] = maxBranch;
        }

    }
    
    
    //neni dodelany ,ze ta matice ma mit 3 prvky apod..dodelat zitra
    //matice prochazeni z nahoru do spoda
    int maxSide = INT_MIN;
    int wholeSUM = INT_MIN;
    int maxSidel = INT_MIN;
    int mainB = 0;
    int mainBl = 0;
    int firstl,secondl,thirdl;
    firstl = secondl = thirdl = -83646;
    //loopin pres radky
    for (int i = 0; i < m; i++){
        for (int j = 0; j < n; j++){
            third = first = second = -83646;          
            firstl = secondl = thirdl = -83646;  
            mainB = 0;
            mainBl = 0;
            for (int k = j; k < n; k++){
                if (i < m-1){
                    maxSide = subDown[i][k]; 
                    if (maxSide > first){
                        third = second;
                        second = first;
                        first = maxSide;
                    } else if (maxSide > second){
                        third = second;
                        second = maxSide;
                    } else if (maxSide > third){
                        third = maxSide;
                    }
                    //count main branch
                    mainB += matrix[i][k];
                    if ((mainB + first + second + third)> wholeSUM){
                        wholeSUM = mainB + first + second + third;
                    }
                }
                if (i > 0){
                    maxSidel = subUpper[i-1][k]; 
                    if (maxSidel > firstl){
                        thirdl = secondl;
                        secondl = firstl;
                        firstl = maxSidel;
                    } else if (maxSidel > secondl){
                        thirdl = secondl;
                        secondl = maxSidel;
                    } else if (maxSidel > thirdl){
                        thirdl = maxSidel;
                    }
                    //count main branch
                    mainBl += matrix[i][k];                     
                 //   printf("max = %d\n", maxSidel);   
               // printf("i,j = %d,%d main B = %d first,second, third = %d %d %d \n", i, k, mainBl, firstl, secondl, thirdl);
                    if ((mainBl + firstl + secondl + thirdl)> wholeSUM){
                        wholeSUM = mainBl + firstl + secondl + thirdl;
                    }
                }
                
            }              
        }
    }
   
    //lookin pres sloupce
    for (int i = 0; i < n; i++){
        for (int j = 0; j < m; j++){
            third = first = second = -83646;          
            firstl = secondl = thirdl = -83646;  
            mainB = 0;
            mainBl = 0;
            for (int k = j; k < m; k++){
                if (i < n-1){
                    maxSide = subRight[k][i]; 
                    if (maxSide > first){
                        third = second;
                        second = first;
                        first = maxSide;
                    } else if (maxSide > second){
                        third = second;
                        second = maxSide;
                    } else if (maxSide > third){
                        third = maxSide;
                    }
                    //count main branch
                    mainB += matrix[k][i];
                    if ((mainB + first + second + third)> wholeSUM){
                        wholeSUM = mainB + first + second + third;
                    }
                    
                }
                if (i > 0){
                    maxSidel = subLeft[k][i-1]; 
                    if (maxSidel > firstl){
                        thirdl = secondl;
                        secondl = firstl;
                        firstl = maxSidel;
                    } else if (maxSidel > secondl){
                        thirdl = secondl;
                        secondl = maxSidel;
                    } else if (maxSidel > thirdl){
                        thirdl = maxSidel;
                    }
                    //count main branch
                    mainBl += matrix[k][i];                     
                    
                    if ((mainBl + firstl + secondl + thirdl)> wholeSUM){
                        wholeSUM = mainBl + firstl + secondl + thirdl;
                    }
                }
                
            }              
        }
    }  
    printf("%d\n", wholeSUM);


} 
