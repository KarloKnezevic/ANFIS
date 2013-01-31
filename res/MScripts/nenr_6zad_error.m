errorLines = dlmread('relError.txt');

x = errorLines(:,1);
y = errorLines(:,2);
z = errorLines(:,3);

scatter3(x,y,z,500,'.','r');
xlabel('x');
ylabel('y');
zlabel('error');
grid on;
axis square;

 print('-djpeg','error.jpg');