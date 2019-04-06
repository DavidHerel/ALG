#include <stdio.h>
#include <limits.h> 


//calculate matrix
int calculateMatrix(int m, int n, int matrix[m][n], int calculatedMatrix[m][n]){
    
       for (int i = 0; i < n; i++){
        calculatedMatrix[0][i] = matrix[0][i];
       }
       
       for (int i=1; i<m; i++){
            for (int j=0; j<n; j++){ 
                calculatedMatrix[i][j] = matrix[i][j] + calculatedMatrix[i-1][j]; 
            }
       }


       for (int i=0; i<m; i++){
            for (int j=1; j<n; j++){ 
                calculatedMatrix[i][j] += calculatedMatrix[i][j-1]; 
            }
       }
            
    
}

// A O(1) time function to compute sum of submatrix 
// between (tli, tlj) and (rbi, rbj) using aux[][] 
// which is built by the preprocess function 
int sumQuery(int m, int n, int calculatedMatrix[m][n], int tli, int tlj, int rbi, int rbj){ 
    // result is now sum of elements between (0, 0) and 
    // (rbi, rbj) 
    int res = calculatedMatrix[rbi][rbj]; 
  
    // Remove elements between (0, 0) and (tli-1, rbj) 
    if (tli > 0) 
       res = res - calculatedMatrix[tli-1][rbj]; 
  
    // Remove elements between (0, 0) and (rbi, tlj-1) 
    if (tlj > 0) 
       res = res - calculatedMatrix[rbi][tlj-1]; 
  
    // Add aux[tli-1][tlj-1] as elements between (0, 0) 
    // and (tli-1, tlj-1) are subtracted twice 
    if (tli > 0 && tlj > 0) 
       res = res + calculatedMatrix[tli-1][tlj-1]; 
  
    return res; 
} 

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
    
    int calculatedMatrix[m][n];
    calculateMatrix(m, n, matrix, calculatedMatrix);
    
    
    printf("------\n");
    //printing     
    for (int i = 0; i < m; i++){        
        for (int j = 0; j < n; j++){
                printf("%d ", matrix[i][j]);               
        }
        printf("\n");
    }
    printf("------\n");
    
    //printing calculated matrix  
    for (int i = 0; i < m; i++){        
        for (int j = 0; j < n; j++){
                printf("%d ", calculatedMatrix[i][j]);               
        }
        printf("\n");
    }
    printf("\n");
    

    //printf("2,2 to 3,4 = %d \n", sumQuery(m, n, calculatedMatrix, 1, 0, 1, 4));
    
    //
    int tmp;
    int index1, index2, index3;
    int maxBranch = INT_MIN;
    int maxMainBranch = INT_MIN;
    int third, second, first;
    third = first = second = INT_MIN;
    int maxSUM = INT_MIN;
    //lets start with all sloupce - now vrati mi to nejvetsi max pro kazdy sloupec
    
    //WHAT NEED TO BE DONE - DO IT FOR LEVA STRANA A DO IT FOR RADKY
    for (int k = 0; k < n-1; k++){
        //lets start from inner way. We start looking for 3 maximumm side branches
        index1 = index2 = index3 = 0;
        third = first = second = INT_MIN;
        for (int i = 0; i < m; i++){
            maxBranch = INT_MIN;
            for (int j = k+1; j < n; j++){
                if (sumQuery(m, n, calculatedMatrix, i, k+1, i, j) > maxBranch){
                    maxBranch = sumQuery(m, n, calculatedMatrix, i, k+1, i, j);
                    printf("i = %d, k+1 = %d,i=%d j=%d", i, k+1, i, j);
                    printf("maxbranc = %d\n,", maxBranch);
                }
            }
            if (maxBranch > first){
                //indexes
                index3 = index2;
                index2 = index1;
                index1 = i;
                //rest
                third = second;
                second = first;
                first = maxBranch;
            } else if (maxBranch > second){
                //indexes
                index3 = index2;
                index2 = i;
                //rest
                third = second;
                second = maxBranch;
            } else if (maxBranch > third){
                index3 = i;
                third = maxBranch;
            }
        }
        if (index1 > index3){
           tmp = index1;
           index1 = index3;
           index3 = tmp;
        }if (index1 > index2){
           tmp = index1;
           index1 = index2;
           index2 = tmp;
        }if (index2 > index3){
           tmp = index2;
           index2 = index3;
           index3 = tmp;
       }
       //printf("index1 = %d, index 3 = %d, \n", index1 , index3);
        //lookin for maximum main branch
        maxMainBranch = INT_MIN;
        for (int i = 0; i <= index1; i++){
            for (int j = index3; j <=m; j++){
                if (sumQuery(m, n, calculatedMatrix, i, k, j, k) > maxMainBranch){
                    maxMainBranch = sumQuery(m, n, calculatedMatrix, index1, k, index3, k);
                }
            }
        }
        
        //count everything
        if ((maxMainBranch + first + second + third) > maxSUM){
            maxSUM = (maxMainBranch + first + second + third);
        }
        
        printf("\n -- maxMainBranch = %d\n", maxMainBranch);
        printf("first = %d, seoncd = %d, third = %d \n", first, second, third);
    }
    printf("maxSUM%d \n", maxSUM);


} 
