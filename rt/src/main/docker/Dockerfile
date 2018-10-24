FROM java:8
VOLUME /tmp
RUN mkdir /app
ADD rt-api.jar /app/app.jar
ADD runboot.sh /app/
RUN bash -c 'touch /app/app.jar'
WORKDIR /app
RUN chmod a+x runboot.sh
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \ 
    echo 'Asia/Shanghai' >/etc/timezone 
EXPOSE 3010
CMD /app/runboot.sh