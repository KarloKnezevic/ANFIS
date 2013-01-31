%Small, Ok, Big
smallEta = dlmread ('SepochEtaError.txt');
appropriateEta = dlmread ('OepochEtaError.txt');
largeEta = dlmread ('BepochEtaError.txt');

plot(smallEta(:,1), log10(smallEta(:,2)), 'r');
hold on;
plot(appropriateEta(:,1), log10(appropriateEta(:,2)), 'g');
plot(largeEta(:,1), log10(largeEta(:,2)), 'b');

xlabel('epoch');
ylabel('log10(error)');
title('Mean Quadratic Error Per Epoch For Different Eta');

legend('small eta', 'appropriate eta', 'large eta');

print('-djpeg','EtaEpochError.jpg');